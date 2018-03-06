#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring

JNICALL
Java_com_famgy_virtualpartner_activity_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "---虚拟伙伴---";
    return env->NewStringUTF(hello.c_str());
}