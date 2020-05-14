#!/bin/bash
gcloud compute project-info add-metadata --metadata serial-port-enable=TRUE
gcloud compute instances create vm1 --zone=us-central1-a --machine-type=f1-micro
gcloud compute instances add-metadata vm1 --metadata serial-port-enable=TRUE --zone=us-central1-a
gcloud compute ssh vm1
gcloud compute connect-to-serial-port vm1
gcloud compute connect-to-serial-port vm1 --port=2
gcloud compute connect-to-serial-port vm1 --port 1 --extra-args max-connections=3 replay-lines=10 on-dtr-low=disconnect

#On the VM
sudo systemctl start serial-getty@ttyS1.service
sudo systemctl enable serial-getty@ttyS1.service
~. # Exit from console 