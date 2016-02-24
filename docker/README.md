docker
======

# How to use this Dockerfile

You can build a docker image based on this Dockerfile. This image will load only Social Data Aggregator Twitter Connector. 
This requires that you have docker installed on your machine.

## Run a container from an image you just built

Once downloaded the [SDA](https://github.com/FiwareTIConsoft/social-data-aggregator) repository code simply navigate to
the docker directory and run
```
    sudo docker build -t fiware/sda .
```
This will build a new docker image and store it locally as
fiware/sda.  The option `-t fiware/sda` gives the image a name. 

To run the container from the newly created image:

```
sudo docker run  fiware/sda 
```

If you want to know more about images and the building process you can find it in [Docker's documentation](https://docs.docker.com/userguide/dockerimages/).
