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
    args: ["--dockerfile=/workspace/dockerfile",
            "--context=dir://workspace",
            "--destination=<user-name>/<repo>"] # replace with your dockerhub account
    volumeMounts:
      - name: kaniko-secret
        mountPath: /kaniko/.docker
      - name: dockerfile-storage
        mountPath: /workspace
  restartPolicy: Never
  volumes:
    - name: kaniko-secret
      secret:
        secretName: regcred
        items:
          - key: .dockerconfigjson
            path: config.json
    - name: dockerfile-storage
      persistentVolumeClaim:
        claimName: dockerfile-claim
''') {
        node(POD_LABEL) {
            container('dockerc') {
                stage("Generate and push docker") {
                    script {
                        checkout scm

                        sh("cat /etc/os-release")


                        def conf = "app/conf.txt"
                        props = readProperties file: conf

                        withCredentials([[$class: 'UsernamePasswordMultiBinding',
                                          credentialsId: 'dockerhub',
                                          usernameVariable: 'USERNAME',
                                          passwordVariable: 'PASSWORD']]) {
                            sh """
                                docker login -u $USERNAME -p $PASSWORD
                            """
                        }



                        sh("docker build -t " + props.dockerRepository + ":" + props.deockerDefaultTag + " .")
                        sh("docker push " + props.dockerRepository + ":" + props.deockerDefaultTag + " ")

                    }
                }
            }
        }
    }
}

