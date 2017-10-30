def call(String name, Closure body) {
    // we ned credentials for skopeo (copy images from openshift to artifactory) and for the artifactory promotion.
    withCredentials([usernameColonPassword(credentialsId: 'artifactory', variable: 'SKOPEO_DEST_CREDENTIALS')]) {
        withEnv(["SKOPEO_SRC_CREDENTIALS=${dockerToken()}", "ARTIFACTORY_BASIC_AUTH=${env.SKOPEO_DEST_CREDENTIALS}"]) {
            def label = name
            podTemplate(cloud: 'openshift', inheritFrom: 'maven', label: label,
                    containers: [containerTemplate(
                            name: 'jnlp',
                            image: 'artifactory.six-group.net/sdbi/jenkins-slave-image-mgmt',
                            alwaysPullImage: true,
                            args: '${computer.jnlpmac} ${computer.name}',
                            workingDir: '/tmp')]
            ) {
                node(label) {
                    body.call()
                }
            }
        }
    }
}