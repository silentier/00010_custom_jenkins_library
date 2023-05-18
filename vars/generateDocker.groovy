def call ( Map popertyInfo ){

    node ("k8s_master") {

        stage ("Generate Docker") {
            checkout scm

            def conf = "app/conf.txt"
            props = readProperties file : conf

            sh ("docker build -t "+props.dockerRepository+":"+props.deockerDefaultTag" .")


        }
    }
}