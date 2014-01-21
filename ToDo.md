 - [STARTED...] learn how to write a stream to a file;

 - [STARTED...] learn how to do pattern replacement in strings;

 - [STARTED...] learn how to capture a pattern from a string;

 - do some actual error/exception checking; particularly, handle these exceptions:

   - Exception in thread "main" java.lang.IllegalArgumentException: Host URL cannot be nil
   - Exception in thread "main" java.lang.ClassCastException: clojure.lang.PersistentList$EmptyList cannot be cast to java.lang.String
   - Exception in thread "main" java.net.ConnectException: Connection refused
   - Exception in thread "main" java.net.MalformedURLException: no protocol: foo

 - consider filtering-out anything but URL strings from the fetched playlist, instead of filtering-out only comments;

 - [DONE] print timing data for each segment fetched;

 - check for the existence of a file (and perhaps compare its size too) before writing to it (skip it if it already exists, and is the expected size);

 - after each segment is downloaded, update and print...
   - ...an ETA, based on elapsed time, bytes downloaded so far, and total count of segments remaining;
   - ...the average bitrate for the download so-far, and total bytes downloaded, since it started;
   - ...the bitrate for the segment just downloaded, and its size;

 - consider using an exponential back-off instead of the current naive back-off mechanism;

 - do the concatentation in the program on-the-fly, instead of relying on a separate process to do it afterwards;
