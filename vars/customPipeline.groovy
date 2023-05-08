def call ( Map popertyInfo ){

    node ("santiago_45.236.128.152") {
        stage ("custom Stages") {
            def conf = "app/conf.txt"
            props = readProperties file : conf

            println props
        }
    }

}