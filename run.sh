#!/bin/sh -x

#URL=http://nrk.no/nordlandsbanen/
#URL=http://nrk.no/nordlandsbanen/media/js/videoPlayer.js
URL=http://nrk_minutt_od_l-vh.akamaihd.net/i/program/program_,141,316,563,1266,2250,.mp4.csmil/index_4_av.m3u8

grm -vf foo.txt

gtime lein run $URL

#lein uberjar
#gtime java -jar target/mustached-hipster-0.1.0-SNAPSHOT-standalone.jar $URL
