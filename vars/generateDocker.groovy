def call ( Map popertyInfo ){
        node("k8s_master") {
            stage("Generate docker") {

                def conf = "app/conf.txt"
                props = readProperties file: conf



                copyArtifacts(
                        fingerprintArtifacts: true,
                        filter: "proyecto_00010_basic1-0.0.1-SNAPSHOT.jar",
                        target: 'proyecto_00010_basic1-0.0.1-SNAPSHOT.jar'
                )

                sh(" ls -la")
                sh("docker build -t "+props.dockerRepository+":"+props.deockerDefaultTag+" .")
            }
        }
}
