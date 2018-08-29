//
// Created by famgy on 6/8/18.
//

#ifndef VIRTUALPARTNER_VPN_MAIN_H
#define VIRTUALPARTNER_VPN_MAIN_H

typedef struct tagArguments {
    int tunnelFd;
}ARGUMENTS_S;

extern void vpn_main(ARGUMENTS_S *pstArgument);

#endif //VIRTUALPARTNER_VPN_MAIN_H
