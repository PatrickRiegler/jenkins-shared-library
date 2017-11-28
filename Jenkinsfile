@groovy.transform.Field
List allFunctions

def gitRevision

node() {
    stage('setup') {
        checkout scm
        gitRevision = sh(returnStdout: true, script: 'git rev-parse --verify HEAD').trim()

        library identifier: "sdbi-shared-lib@${gitRevision}", retriever: modernSCM(
                [$class       : 'GitSCMSource',
                 remote       : 'ssh://git@stash.six-group.net:22/sdbi/jenkins-shared-library.git',
                 credentialsId: 'six-baseimages-bitbucket-secret'])
        dir('vars') {
            allFunctions = sh(returnStdout: true, script: 'ls *.groovy | sed -e "s/\\.groovy$//"').trim().split('\n').toList()
        }
    }


    runTestsFor('defaultProperties') {
        // TODO
    }

    runTestsFor('getDockerToken') {

        String token = sh(returnStdout: true, script: 'cat ${AUTH_TOKEN}').trim()

        def tokenDefault = getDockerToken()
        def tokenWithLogin = getDockerToken('myLogin')
        def tokenNoLogin = getDockerToken(null)

        assert tokenDefault.startsWith('serviceaccount:'): "The default token '${tokenDefault}' should start with login 'serviceaccount'"
        assert tokenDefault.endsWith(":${token}"): "The default token '${tokenDefault}' should end with the token '${token}'"

        assert tokenWithLogin.startsWith('myLogin:'): "The token ${tokenWithLogin}' should start with login 'myLogin'"
        assert tokenWithLogin.endsWith(":${token}"): "The token '${tokenDefault}' should end with the token '${token}'"

        assert tokenNoLogin == token: "The token without login '${tokenNoLogin}' should be '${token}'"
    }

    runTestsFor('getImageStreamRegistryUrl') {
        // get an image stream from the current project
        String imageStreamName = sh(returnStdout: true, script: 'oc get is -o name | head -n 1 | sed -e "s/imagestreams\\///"').trim()
        String url = getImageStreamRegistryUrl(imageStreamName)

        assert url.contains(imageStreamName): "The image stream URL '${url}' sould contain the image stream name '${imageStreamName}'"
    }

    runTestsFor('getInitialJobContext') {
        def jobContext = getInitialJobContext()

        assert jobContext.currentBuildVersion: 'The currentBuildVersion variable should be set in the initial context'

        jobContext = getInitialJobContext(false)

        assert !jobContext.currentBuildVersion: 'The currentBuildVersion variable should NOT be set in the initial context'
    }

    runTestsFor('imageMgmtNode') {
        imageMgmtNode("function-tests") {
            def skopeoCopyExists = sh returnStatus: true, script: 'which skopeoCopy.sh'

            assert skopeoCopyExists == 0: "skopeoCopy.sh should exist if we are running in the imageMgmtNode"

            def promoteToArtifactoryExists = sh returnStatus: true, script: 'which promoteToArtifactory.sh'

            assert promoteToArtifactoryExists == 0: "promoteToArtifactory.sh should exist if we are running in the imageMgmtNode"
        }
    }

    runTestsFor('getGitRevision') {
        def longRevision = getGitRevision(false)
        assert longRevision == gitRevision: "The git revision '${longRevision}' should be '${gitRevision}'"

        def shortRevision = getGitRevision(true)
        assert gitRevision.startsWith(shortRevision): "The short git revision '${shortRevision}' should be the first part of the full revision '${gitRevision}'"

        def defaultRevision = getGitRevision()
        assert defaultRevision == shortRevision: "The default value ''${defaultRevision} should be the sourt revision '${shortRevision}'"
    }

    verifyTests()
}


def runTestsFor(String functionName, Closure body) {
    stage("Test ${functionName}") {
        echo "\nTesting function '${functionName}\n========================================="
        body.call()
    }
    allFunctions.removeAll([functionName])
}


def verifyTests() {
    stage("Verify all tested") {
        assert allFunctions.empty: "Not all functions have been tested. Untested functions are ${allFunctions}"
    }
}
