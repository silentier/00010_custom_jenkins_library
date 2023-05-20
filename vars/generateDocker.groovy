def call ( Map popertyInfo ){
    podTemplate(yaml: '''
    apiVersion: v1
    kind: Pod
    metadata:
        namespace: devops-tools
    spec:
      containers:
      - name: maven
        image: maven:3.8.1-jdk-8
        command:
        - sleep
        args:
        - 99d
      - name: kaniko
        image: gcr.io/kaniko-project/executor:debug
        command:
        - sleep
        args:
        - 9999999
        volumeMounts:
        - name: kaniko-secret
          mountPath: /kaniko/.docker
      restartPolicy: Never
      volumes:
      - name: kaniko-secret
        secret:
            secretName: dockercred
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

