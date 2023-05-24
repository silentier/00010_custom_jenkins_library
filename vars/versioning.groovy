def call ( Map popertyInfo ){
    podTemplate(yaml: '''
apiVersion: v1
kind: Pod
metadata:
  namespace: devops-tools
spec:
  containers:
  - name: maven
    image: silentier/00010_golden_image_slave_jenkins:2023_05_20_17_39_40
    command:
    - sleep
    args:
    - 99d
    volumeMounts:
     - mountPath: "/root/.m2/"
       name: mvn-repository
  - name: git
    image: bitnami/git:latest
    command:
    - sleep
    args:
    - 99d    
  volumes:
    - name: mvn-repository
      persistentVolumeClaim:
''') {
        node(POD_LABEL) {

            environment {
                BRANCH="main"
            }


            container('maven') {

                stage("Versioning") {
                    checkout scm

                    sh("cat /etc/os-release")

                    def conf = "app/conf.txt"
                    props = readProperties file: conf

                    switch (props.compileMethod) {
                        case "mvn":
                            configFileProvider([configFile(fileId: 'd9f13ed0-a67a-4c59-81d9-f6034324ed8b', variable: 'config')]) {
                                OLD_VERSION=sh(script:"mvn help:evaluate -Dexpression=project.version -q -DforceStdout" , returnStdout: true).trim()
                                println "OLD_VERSION:"+OLD_VERSION

                                parte1='${parsedVersion.majorVersion}'
                                parte2='${parsedVersion.minorVersion}'
                                parte3='${parsedVersion.nextIncrementalVersion}'
                                sh("""mvn build-helper:parse-version versions:set -DnewVersion=\\${parte1}.\\${parte2}.\\${parte3} versions:commit -s ${config} """)

                                NEW_VERSION=sh(script:"mvn help:evaluate -Dexpression=project.version -q -DforceStdout" , returnStdout: true).trim()
                                println "NEW_VERSION:"+NEW_VERSION

                                currentBuild.displayName = NEW_VERSION
                                //currentBuild.description = BRANCH
                            }

                            break
                        default:
                            println "default"
                            break
                    }

                }
            }
            container('git') {
               stage("Tag") {

                   PWD=sh(script:"pwd", returnStdout:true).trim()
                   sh("ls -la")
                   sh(" git config --global --add safe.directory ${PWD} ")

                   withCredentials([[$class: 'UsernamePasswordMultiBinding',
                                     credentialsId: 'administratorsilentier',
                                     usernameVariable: 'USERNAME',
                                     passwordVariable: 'PASSWORD']]) {
                       sh("""
                        git config user.name "Jenkins"
                        git config user.email "administrator@silentier.com"
                        git config  url.https://${USERNAME}:${PASSWORD}@github.com/.insteadof https://github.com
                       """)
                   }



                   def BRANCH = scm.branches[0].name

                   sh("git add .")
                   sh("git commit -m 'build ${NEW_VERSION}' ")
                   sh("git push origin HEAD:${BRANCH}")
                }
            }
        }
    }
}