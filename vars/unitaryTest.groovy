def call ( Map popertyInfo ){

    node ("k8s_master") {

        stage ("Unitary Tests") {
            checkout scm

            def conf = "app/conf.txt"
            props = readProperties file : conf


            configFileProvider([configFile(fileId: 'd9f13ed0-a67a-4c59-81d9-f6034324ed8b', variable: 'config')]) {
                sh ("mvn -version")
                sh ("mvn "+props.testMethod+" -s ${config} ")
            }

        }
    }
}