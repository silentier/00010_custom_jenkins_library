def call ( Map popertyInfo ){
    podTemplate(yaml: '''
              kind: Pod
              metadata:
                namespace: devops-tools
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
                    - name: jenkins-docker-cfg
                      mountPath: /kaniko/.docker
                volumes:
                - name: jenkins-docker-cfg
                  projected:
                    sources:
                    - secret:
                        name: docker-secret
                        items:
                          - key: .dockerconfigjson
                            path: config.json

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

