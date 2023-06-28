def call ( Map popertyInfo ) {
        podTemplate(yaml: '''
apiVersion: v1
kind: Pod
metadata:
  namespace: devops-tools
spec:
  containers:
  - name: maven
    image: silentier/00010_golden_image_slave_jenkins
    imagePullPolicy: Always
    command:
    - sleep
    args:
    - 99d
''') {
            node(POD_LABEL) {
                container('maven') {
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
