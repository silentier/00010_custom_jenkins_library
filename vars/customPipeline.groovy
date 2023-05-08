def call ( Map popertyInfo ){

    stage ("Check File") {
        def conf = "app/conf.txt"
        props = readProperties file : conf

        println props
    }

}