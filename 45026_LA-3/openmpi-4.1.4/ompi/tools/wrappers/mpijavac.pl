#!/usr/bin/env perl

# WARNING: DO NOT EDIT THE mpijava.pl FILE AS IT IS GENERATED!
#          MAKE ALL CHANGES IN mpijava.pl.in

# Copyright (c) 2011-2013 Cisco Systems, Inc.  All rights reserved.
# Copyright (c) 2012      Oracle and/or its affiliates.  All rights reserved.

use strict;

# The main purpose of this wrapper compiler is to check for
# and adjust the Java class path to include the OMPI classes
# in mpi.jar. The user may have specified a class path on
# our cmd line, or it may be in the environment, so we have
# to check for both. We also need to be careful not to
# just override the class path as it probably includes classes
# they need for their application! It also may already include
# the path to mpi.jar, and while it doesn't hurt anything, we
# don't want to include our class path more than once to avoid
# user astonishment

# Let the build system provide us with some critical values
my $my_compiler = "";
my $mpi_jar   = "/home/administrator/opt/openmpi/lib/mpi.jar";
my $shmem_jar = "/home/administrator/opt/openmpi/lib/shmem.jar";

# globals
my $showme_arg = 0;
my $verbose = 0;
my $my_arg;

# Cannot use the usual GetOpts library as the user might
# be passing -options to the Java compiler! So have to
# parse the options ourselves to look for help and showme
my @save_args;
foreach $my_arg (@ARGV) {
    if ($my_arg eq "-h" ||
        $my_arg eq "--h" ||
        $my_arg eq "-help" ||
        $my_arg eq "--help") {
        print "Options:
  --showme                      Show the wrapper compiler command without executing it
  --verbose                     Show the wrapper compiler command *and* execute it
  --help | -h                   This help list\n";
        exit(0);
    } elsif ($my_arg eq "--showme") {
        $showme_arg = 1;
    } elsif ($my_arg eq "--verbose") {
        $verbose = 1;
    } else {
        push(@save_args, $my_arg);
    }
}

# Create a place to save our argv array so we can edit any
# provide class path option
my @arguments = ();

# Check the command line for a class path
my $cp_found = 0;
my $my_cp;
foreach $my_arg (@save_args) {
    if (1 == $cp_found) {
        $my_cp = $my_arg;
        if (0 > index($my_arg, "mpi.jar")) {
            # not found, so we add our path
            if (rindex($my_arg, ":") == length($my_arg)-1) {
                # already have a colon at the end
                $my_cp = $my_cp . $mpi_jar;
            } else {
                # need to add the colon between paths
                $my_cp = $my_cp . ":" . $mpi_jar;
            }
        }
        if (0 > index($my_arg, "shmem.jar")) {
            # not found, so we add our path
            if (rindex($my_arg, ":") == length($my_arg)-1) {
                # already have a colon at the end
                $my_cp = $my_cp . $shmem_jar;
            } else {
                # need to add the colon between paths
                $my_cp = $my_cp . ":" . $shmem_jar;
            }
        }
        push(@arguments, $my_cp);
        $cp_found = 2;
    } else {
        if (0 == $cp_found && (
            0 <= index($my_arg, "-cp") ||
            0 <= index($my_arg, "-classpath")))
        {
            $cp_found = 1;
        }
        push(@arguments, $my_arg);
    }
}

# If the class path wasn't found on the cmd line, then
# we next check the class path in the environment, if it exists
if (2 != $cp_found && exists $ENV{'CLASSPATH'} && length($ENV{'CLASSPATH'}) > 0) {
    $my_cp = $ENV{'CLASSPATH'};
    if(0 > index($my_cp, "mpi.jar")) {
        # not found, so we add our path
        if (rindex($my_cp, ":") == length($my_cp)-1) {
            # already have a colon at the end
            $my_cp = $my_cp . $mpi_jar;
        } else {
            # need to add the colon between paths
            $my_cp = $my_cp . ":" . $mpi_jar;
        }
    }
    if (0 > index($my_cp, "shmem.jar")) {
        # not found, so we add our path
        if (rindex($my_cp, ":") == length($my_cp)-1) {
            # already have a colon at the end
            $my_cp = $my_cp . $shmem_jar;
        } else {
            # need to add the colon between paths
            $my_cp = $my_cp . ":" . $shmem_jar;
        }
    }
    unshift(@arguments, $my_cp);
    unshift(@arguments, "-cp");
    # ensure we mark that we "found" the class path
    $cp_found = 1;
}

# If the class path wasn't found in either location, then
# we have to insert it as the first argument
if (0 == $cp_found) {
    unshift(@arguments, $mpi_jar . ":" . $shmem_jar);
    unshift(@arguments, "-cp");
}

# Construct the command
my $returnCode = 0;
if ($showme_arg) {
    print "$my_compiler @arguments\n";
} else {
    if ($verbose) {
        print "$my_compiler @arguments\n";
    }
    $returnCode = system $my_compiler, @arguments;
}
exit $returnCode;
