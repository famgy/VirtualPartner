#ifndef _COMMON_H_
#define _COMMON_H_

#include <stddef.h>

#define ERROR_SUCCESS 0
#define ERROR_FAILED -1

extern size_t strlcpy(char *destStr, const char *srcStr, size_t size);

#endif /* _COMMON_H_ */
