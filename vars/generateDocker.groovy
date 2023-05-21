def call ( Map popertyInfo ){
    podTemplate(yaml: '''
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
      persistentVolumeClaim:
        claimName: dockerfile-claim
''')
            {
        node(POD_LABEL) {
            container('kaniko') {
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



                    //sh("docker build -t " + props.dockerRepository + ":" + props.deockerDefaultTag + " .")
                    // sh("docker push " + props.dockerRepository + ":" + props.deockerDefaultTag + " ")

                    }
                }
            }
        }
    }
}

