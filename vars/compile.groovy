def call ( Map popertyInfo ){

    node ("k8s_master") {

        stage ("Compile") {
            checkout scm

            sh("cat /etc/os-release")

            def conf = "app/conf.txt"
            props = readProperties file : conf

            println props
            println "compileMethod:"+props.compileMethod

            switch (props.compileMethod) {
                case "mvn":
                    sh ("mvn "+props.compileCommand)
                    break
                default:
                    println "default"
                    break
            }
        }
    }
}