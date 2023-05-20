def call ( Map popertyInfo ){
    podTemplate(yaml: '''
apiVersion: v1
kind: Pod
metadata:
  namespace: devops-tools
spec:
  containers:
  - name: maven
    image: silentier/00010_golden_image_slave_jenkins:latest
    command:
    - sleep
    args:
    - 99d
    volumeMounts:
     - mountPath: "/root/.m2/"
     name: mvn-repository
---
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: mvn-repository
  namespace: devops-tools
spec:
  storageClassName: local-storage
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 3Gi
''') {
        node(POD_LABEL) {
            container('maven') {
                stage("Compile") {
                    checkout scm

                    sh("cat /etc/os-release")

                    def conf = "app/conf.txt"
                    props = readProperties file: conf

                    println props
                    println "compileMethod:" + props.compileMethod

                    switch (props.compileMethod) {
                        case "mvn":
                            configFileProvider([configFile(fileId: 'd9f13ed0-a67a-4c59-81d9-f6034324ed8b', variable: 'config')]) {
                                sh("mvn -version")
                                sh("mvn " + props.compileCommand + " -s ${config} ")
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