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
''') {
        node(POD_LABEL) {
            container('maven') {

                stage("Versioning") {
                    checkout scm

                    sh("cat /etc/os-release")

                    def conf = "app/conf.txt"
                    props = readProperties file: conf

                    switch (props.compileMethod) {
                        case "mvn":
                            configFileProvider([configFile(fileId: 'd9f13ed0-a67a-4c59-81d9-f6034324ed8b', variable: 'config')]) {
                                sh("mvn -version")
                                parte1='${parsedVersion.majorVersion}'
                                parte2='${parsedVersion.minorVersion}'
                                parte3='${parsedVersion.nextIncrementalVersion}'
                                sh("""mvn mvn build-helper:parse-version versions:set -DnewVersion=\\${parte1}.\\${parte2}.\\${parte3} versions:commit -s ${config} """)
                            }

                            break
                        default:
                            println "default"
                            break
                    }

                }
            }
        }
    }
}