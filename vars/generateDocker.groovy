def call ( Map popertyInfo ){
    podTemplate(yaml: '''
apiVersion: v1
kind: Pod
  spec:
    containers:
    - name: kaniko
      image: gcr.io/kaniko-project/executor:v1.6.0-debug
      imagePullPolicy: Always
      command:
      - sleep
      args:
      - 99d
      volumeMounts:
        - name: dockerfile-storage
        mountPath: /workspace
  restartPolicy: Never
  volumes:
    - name: dockerfile-storage
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
