//
// Created by famgy on 6/8/18.
//

#include "vpn_main.h"
#include <jni.h>

JNIEXPORT void JNICALL
Java_com_famgy_virtualpartner_service_PlusVpnService_jniRun(JNIEnv *env, jint tunnelFd) {
    ARGUMENTS_S stArgs;

    stArgs.tunnelFd = tunnelFd;

    vpn_main(&stArgs);
}

JNIEXPORT void JNICALL
Java_com_famgy_virtualpartner_service_PlusVpnService_jniStop(JNIEnv *env, jint tunnelFd) {


}