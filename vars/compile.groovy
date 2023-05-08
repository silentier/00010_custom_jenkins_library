def call ( Map popertyInfo ){

    node ("k8s_master") {

        stage ("Compile") {
            checkout scm

            def conf = "app/conf.txt"
            props = readProperties file : conf

            println props
            println "compileMethod:"+props.compileMethod

            switch (props.compileMethod) {
                case "mvn":
                    println "mvn"
                    break
                default:
                    println "default"
                    break
            }
        }
    }
}