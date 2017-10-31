# Shared jenkins function library for Six docker base image builds

## Documentation
https://jenkins.io/doc/book/pipeline/shared-libraries/


## Usage
```
library identifier: 'sdbi-shared-lib@develop', retriever: modernSCM(
        [$class       : 'GitSCMSource',
         remote       : 'ssh://git@stash.six-group.net:22/sdbi/jenkins-shared-library.git',
         credentialsId: 'six-baseimages-bitbucket-secret'])
```
