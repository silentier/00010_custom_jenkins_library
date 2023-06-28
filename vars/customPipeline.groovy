def call ( Map popertyInfo ) {
        podTemplate(yaml: '''
apiVersion: v1
kind: Pod
metadata:
  namespace: devops-tools
  name: esta_es_una_prueba
spec:
  containers:
  - name: maven2
    image: silentier/00010_golden_image_slave_jenkins:2023_06_28_04_17_53
    imagePullPolicy: Always
    command:
    - sleep
    args:
    - 99d
''') {
            node(POD_LABEL) {
                container('maven2') {
                    stage('Stages') {

                        script {
                            println "***************** V2.0"
                            sh ("cat /etc/os-release")
                            sh("node --version")
                            sh("npm --version")
                            sh("ng version")

                            versioning();
                            compile();
                            unitaryTest();
                            packageAndGenerateStage();
                            restartDeployment();
                        }
                    }
                }
            }
        }
    }
