def call ( Map popertyInfo ) {
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
''') {
            node(POD_LABEL) {
                container('maven') {
                    stage('check version') {
                        compile();
                        unitaryTest();
                        packageStage();
                        generateDocker();
                    }
                }
            }
        }
    }
