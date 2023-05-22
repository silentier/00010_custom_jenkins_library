def call ( Map popertyInfo ) {
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
                    stage('Stages') {

                        script {
                            sh("cat /etc/os-release")

                            versioning();
                            compile();
                            unitaryTest();
                            packageAndGenerateStage();

                        }
                    }
                }
            }
        }
    }
