def call ( Map popertyInfo ){
        node("k8s_master") {
            stage("Generate docker") {

                def conf = "app/conf.txt"
                props = readProperties file: conf

                sh("docker build -t "+props.dockerRepository+":"+props.deockerDefaultTag+" .")
            }
        }
}
