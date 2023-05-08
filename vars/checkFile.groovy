def call ( Map popertyInfo ){

    node ("santiago") {

        stage ("Check File") {
            def conf = "app/conf.txt"
            props = readProperties file : conf

            println props
        }
    }
}