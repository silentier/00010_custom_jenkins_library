def call ( Map popertyInfo ) {
    node("k8s_master") {
        stage("restart service") {
                checkout scm

                def conf = "app/conf.txt"
                props = readProperties file: conf
                println props

                DEPLOYMENT_NAME = props.deploymentName


                sh(" kubectl rollout restart deployment ${DEPLOYMENT_NAME} -n silentier ")

        }
    }
}