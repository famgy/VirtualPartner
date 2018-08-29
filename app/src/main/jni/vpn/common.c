
#include "common.h"
#include <string.h>

size_t strlcpy(char *destStr, const char *srcStr, size_t size)
{
    size_t ret = strlen(srcStr);
    if (size)
    {
        size_t len = (ret >= size)?size-1:ret;
        memcpy(destStr, srcStr, len);
        destStr[len] = '\0';
    }

    return ret;
}
