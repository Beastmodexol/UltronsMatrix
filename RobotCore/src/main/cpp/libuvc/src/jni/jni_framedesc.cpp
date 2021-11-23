/*
Copyright (c) 2017 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
//
// UvcFrameDesc methods
//
#include <jni.h>
#include <unistd.h>
#include <libuvc.h>
#include "ftc.h"

#undef TAG
static LPCSTR TAG = "UvcFrameDesc";

JNIEXPORT jintArray JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcFrameDesc_nativeGetFieldOffsets(JNIEnv *env, jclass type, jint cFieldExpected)
    {
    OFFSET_MAP_START(15, uvc_frame_desc)

    #define addField(field)  (cFieldAdded++, (*pT++) = offsetof(uvc_frame_desc, field))
    addField(bDescriptorSubtype);
    addField(bFrameIndex);
    addField(bmCapabilities);
    addField(wWidth);
    addField(wHeight);

    addField(dwMinBitRate);
    addField(dwMaxBitRate);
    addField(dwMaxVideoFrameBufferSize);
    addField(dwDefaultFrameInterval);
    addField(dwMinFrameInterval);

    addField(dwMaxFrameInterval);
    addField(dwFrameIntervalStep);
    addField(bFrameIntervalType);
    addField(dwBytesPerLine);
    addField(rgIntervals);
    #undef addField

    OFFSET_MAP_END()
    }
