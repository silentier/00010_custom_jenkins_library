def call ( Map popertyInfo ) {

    environment {
        DOCKERHUB_CREDENTIALS=credentials("dockerhub")
    }

    node("k8s_master") {
        stage ("Generate Docker and Push") {
            def conf = "app/conf.txt"
            props = readProperties file: conf

            sh("ls -la")
            sh 'docker login -u $DOCKERHUB_CREDENTIALS_USR -p $DOCKERHUB_CREDENTIALS_PSW '
            sh("docker build -t " + props.dockerRepository + ":" + props.deockerDefaultTag + " .")

        }
    }
}