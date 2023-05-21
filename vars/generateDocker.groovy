def call ( Map popertyInfo ){
    podTemplate(yaml: '''
apiVersion: v1
kind: Pod
metadata:
  namespace: devops-tools
  name: kaniko
spec:
  containers:
  - name: kaniko
    image: gcr.io/kaniko-project/executor:latest
    volumeMounts:
      - name: dockerfile-storage
        mountPath: /workspace
  restartPolicy: Never
  volumes:
    - name: dockerfile-storage
      persistentVolumeClaim:
        claimName: dockerfile-claim
''') {
        node(POD_LABEL) {
            container('maven') {
                stage("Generate docker") {
                    checkout scm

                    sh("cat /etc/os-release")

                }
            }
        }
    }
}
