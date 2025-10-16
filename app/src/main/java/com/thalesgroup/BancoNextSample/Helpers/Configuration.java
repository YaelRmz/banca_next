/*
 * MIT License
 *
 * Copyright (c) 2022 Thales DIS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * IMPORTANT: This source code is intended to serve training information purposes only.
 *            Please make sure to review our IdCloud documentation, including security guidelines.
 */

package com.thalesgroup.BancoNextSample.Helpers;

import com.gemalto.idp.mobile.fasttrack.MobileTlsConfiguration;

import java.nio.charset.StandardCharsets;

public class Configuration {

    /**
     * Activation code is used to enable individual supported features
     */
    public static final String CFG_ACTIVATION_CODE = "";

    //region SecureLog configuration

    /**
     * The modulus for SecureLog configuration
     */
    public static final byte[] CFG_SLOG_MODULUS = new byte[]{
            (byte) 0x00, (byte) 0xd4, (byte) 0x6d, (byte) 0x5c, (byte) 0x06, (byte) 0x35, (byte) 0xb0,
            (byte) 0x52, (byte) 0x2f, (byte) 0x3e, (byte) 0xf4, (byte) 0x14, (byte) 0xd8, (byte) 0x3d,
            (byte) 0xf2, (byte) 0xd7, (byte) 0xf5, (byte) 0x1b, (byte) 0x54, (byte) 0x7e, (byte) 0x01,
            (byte) 0x0b, (byte) 0x1c, (byte) 0x23, (byte) 0x60, (byte) 0x04, (byte) 0xde, (byte) 0x4c,
            (byte) 0x67, (byte) 0x3e, (byte) 0xf8, (byte) 0x3b, (byte) 0x2b, (byte) 0xdd, (byte) 0xfa,
            (byte) 0x50, (byte) 0x87, (byte) 0xe7, (byte) 0xb3, (byte) 0x03, (byte) 0x22, (byte) 0x93,
            (byte) 0x87, (byte) 0xdd, (byte) 0xaf, (byte) 0x0a, (byte) 0xdd, (byte) 0xf9, (byte) 0xee,
            (byte) 0x8b, (byte) 0x60, (byte) 0x45, (byte) 0x1a, (byte) 0x6b, (byte) 0xf9, (byte) 0x49,
            (byte) 0xfd, (byte) 0x64, (byte) 0x0f, (byte) 0xbd, (byte) 0xe1, (byte) 0x85, (byte) 0x7e,
            (byte) 0x40, (byte) 0xe1, (byte) 0x52, (byte) 0x10, (byte) 0xec, (byte) 0xae, (byte) 0x93,
            (byte) 0xfd, (byte) 0x61, (byte) 0xb7, (byte) 0xfc, (byte) 0xdb, (byte) 0x5f, (byte) 0x60,
            (byte) 0xa0, (byte) 0xbf, (byte) 0x10, (byte) 0x94, (byte) 0x76, (byte) 0x15, (byte) 0x8c,
            (byte) 0x9b, (byte) 0x7c, (byte) 0xcd, (byte) 0xd7, (byte) 0xa7, (byte) 0xa5, (byte) 0x29,
            (byte) 0x1f, (byte) 0x31, (byte) 0x9a, (byte) 0xd0, (byte) 0x2e, (byte) 0xa2, (byte) 0x4f,
            (byte) 0x26, (byte) 0xe9, (byte) 0x14, (byte) 0x98, (byte) 0x99, (byte) 0xa6, (byte) 0x12,
            (byte) 0x1c, (byte) 0xb5, (byte) 0xac, (byte) 0x19, (byte) 0x99, (byte) 0xae, (byte) 0x23,
            (byte) 0xc8, (byte) 0x75, (byte) 0xea, (byte) 0xc0, (byte) 0xe0, (byte) 0x10, (byte) 0x31,
            (byte) 0x02, (byte) 0xf1, (byte) 0x4a, (byte) 0x97, (byte) 0xa5, (byte) 0xe2, (byte) 0xb0,
            (byte) 0xfd, (byte) 0x06, (byte) 0x70, (byte) 0xd2, (byte) 0xa5, (byte) 0x5a, (byte) 0xed,
            (byte) 0xe2, (byte) 0x9e, (byte) 0xea, (byte) 0x6f, (byte) 0x05, (byte) 0x06, (byte) 0x64,
            (byte) 0xa0, (byte) 0xf3, (byte) 0x5d, (byte) 0xba, (byte) 0x48, (byte) 0x4b, (byte) 0x18,
            (byte) 0xd1, (byte) 0x7b, (byte) 0xef, (byte) 0x48, (byte) 0x22, (byte) 0x8f, (byte) 0xdb,
            (byte) 0x5c, (byte) 0x07, (byte) 0xf0, (byte) 0x96, (byte) 0xfe, (byte) 0xfb, (byte) 0xac,
            (byte) 0xf1, (byte) 0xb0, (byte) 0x13, (byte) 0x0d, (byte) 0x3f, (byte) 0xe0, (byte) 0x8e,
            (byte) 0x81, (byte) 0xae, (byte) 0x73, (byte) 0xef, (byte) 0x5c, (byte) 0xd4, (byte) 0x11,
            (byte) 0x37, (byte) 0x85, (byte) 0x80, (byte) 0x9f, (byte) 0xdc, (byte) 0x19, (byte) 0x05,
            (byte) 0x49, (byte) 0xde, (byte) 0x34, (byte) 0xfe, (byte) 0x20, (byte) 0x54, (byte) 0x2d,
            (byte) 0xe6, (byte) 0xcc, (byte) 0x33, (byte) 0x19, (byte) 0x82, (byte) 0x0c, (byte) 0xc5,
            (byte) 0x9e, (byte) 0x42, (byte) 0xbe, (byte) 0x27, (byte) 0xf2, (byte) 0x7b, (byte) 0xaa,
            (byte) 0xfc, (byte) 0x7f, (byte) 0x11, (byte) 0x43, (byte) 0x83, (byte) 0x8c, (byte) 0xde,
            (byte) 0x71, (byte) 0xdd, (byte) 0x8b, (byte) 0xd5, (byte) 0x08, (byte) 0xb7, (byte) 0xcc,
            (byte) 0xc5, (byte) 0x0a, (byte) 0xf9, (byte) 0x91, (byte) 0xdc, (byte) 0x78, (byte) 0x68,
            (byte) 0x12, (byte) 0x64, (byte) 0x9d, (byte) 0x35, (byte) 0x89, (byte) 0x1e, (byte) 0xcc,
            (byte) 0x23, (byte) 0x7a, (byte) 0x11, (byte) 0x21, (byte) 0x77, (byte) 0x2a, (byte) 0xc4,
            (byte) 0xad, (byte) 0xc4, (byte) 0x2f, (byte) 0xcf, (byte) 0xec, (byte) 0x21, (byte) 0x50,
            (byte) 0x9e, (byte) 0x32, (byte) 0xf9, (byte) 0xa3, (byte) 0x2a, (byte) 0x27, (byte) 0x33,
            (byte) 0x27, (byte) 0x4d, (byte) 0x24, (byte) 0x78, (byte) 0x59
    };

    /**
     * The exponent for SecureLog configuration
     */
    public static final byte[] CFG_SLOG_EXPONENT = new byte[]{
            (byte) 0x01, (byte) 0x00, (byte) 0x01
    };

    public static final String CFG_PROVISIONING_URL = "https://provisioner-eps-demo.rnd.gemaltodigitalbankingidcloud.com/provisioner/domains/bradesco/provision";

    /**
     * Identifier for the EPS server’s public RSA key.
     */
    public static final String CFG_RSA_KEY_ID = "A0348F77DB3C059B60DD285A0CA69CC5006A8B20";

    /**
     * The RSA modulus of the EPS public key (on provisioning protocol level, not transport level).
     */
    public static final byte[] CFG_RSA_KEY_MODULUS = {
            (byte) 0x00,(byte) 0x9b,(byte) 0x2f,(byte) 0xf8,(byte) 0xd1,(byte) 0xb2,(byte) 0x9d,(byte) 0x97,
            (byte) 0xda,(byte) 0x06,(byte) 0xf0,(byte) 0xe3,(byte) 0x02,(byte) 0x6e,(byte) 0x5c,(byte) 0x96,
            (byte) 0x2c,(byte) 0x1e,(byte) 0x21,(byte) 0xbe,(byte) 0xe7,(byte) 0x08,(byte) 0xb1,(byte) 0x1b,
            (byte) 0x07,(byte) 0x43,(byte) 0x2c,(byte) 0xa9,(byte) 0x1e,(byte) 0x31,(byte) 0x19,(byte) 0xd1,
            (byte) 0x7a,(byte) 0x0e,(byte) 0xc8,(byte) 0x77,(byte) 0x7d,(byte) 0xa1,(byte) 0xeb,(byte) 0x69,
            (byte) 0x88,(byte) 0xc4,(byte) 0x93,(byte) 0x0b,(byte) 0x2a,(byte) 0x0b,(byte) 0x47,(byte) 0xc6,
            (byte) 0xb6,(byte) 0x6b,(byte) 0x72,(byte) 0xd6,(byte) 0xda,(byte) 0xe0,(byte) 0xb9,(byte) 0xfc,
            (byte) 0xed,(byte) 0xc7,(byte) 0x05,(byte) 0xc5,(byte) 0x9f,(byte) 0xff,(byte) 0x69,(byte) 0x36,
            (byte) 0x89,(byte) 0xac,(byte) 0x2f,(byte) 0x8b,(byte) 0xe7,(byte) 0x47,(byte) 0xa8,(byte) 0x2c,
            (byte) 0xad,(byte) 0x4a,(byte) 0xb3,(byte) 0x3b,(byte) 0x5e,(byte) 0x53,(byte) 0x37,(byte) 0x89,
            (byte) 0xb8,(byte) 0xb6,(byte) 0xf4,(byte) 0x90,(byte) 0x1a,(byte) 0xee,(byte) 0xe7,(byte) 0xb8,
            (byte) 0x0a,(byte) 0x77,(byte) 0x93,(byte) 0xe2,(byte) 0x6c,(byte) 0x24,(byte) 0x52,(byte) 0xa2,
            (byte) 0xc4,(byte) 0x56,(byte) 0xa2,(byte) 0x2d,(byte) 0x81,(byte) 0x81,(byte) 0x9f,(byte) 0xc0,
            (byte) 0xae,(byte) 0xe7,(byte) 0xff,(byte) 0x82,(byte) 0x1b,(byte) 0xa1,(byte) 0x19,(byte) 0x46,
            (byte) 0x39,(byte) 0x61,(byte) 0x51,(byte) 0xf0,(byte) 0x6d,(byte) 0x98,(byte) 0xf6,(byte) 0x0b,
            (byte) 0x29,(byte) 0x64,(byte) 0x27,(byte) 0x70,(byte) 0xb1,(byte) 0xf3,(byte) 0x29,(byte) 0x04,
            (byte) 0x14,(byte) 0xce,(byte) 0x48,(byte) 0x84,(byte) 0x4c,(byte) 0xb6,(byte) 0x6e,(byte) 0x88,
            (byte) 0x2c,(byte) 0x7b,(byte) 0xc5,(byte) 0xf4,(byte) 0xf0,(byte) 0x65,(byte) 0x00,(byte) 0x6d,
            (byte) 0xf1,(byte) 0x0c,(byte) 0x56,(byte) 0x7b,(byte) 0x60,(byte) 0x43,(byte) 0x5c,(byte) 0x33,
            (byte) 0x52,(byte) 0x79,(byte) 0x00,(byte) 0xcb,(byte) 0xb4,(byte) 0x3e,(byte) 0x7d,(byte) 0x0d,
            (byte) 0xc6,(byte) 0x0f,(byte) 0xe9,(byte) 0x2a,(byte) 0x42,(byte) 0x71,(byte) 0xd0,(byte) 0x6c,
            (byte) 0xc6,(byte) 0x43,(byte) 0x2b,(byte) 0xd0,(byte) 0x87,(byte) 0xd5,(byte) 0x5e,(byte) 0xf8,
            (byte) 0xa2,(byte) 0x74,(byte) 0xed,(byte) 0x3d,(byte) 0x70,(byte) 0x53,(byte) 0x1f,(byte) 0x4f,
            (byte) 0x7a,(byte) 0xdb,(byte) 0x36,(byte) 0x32,(byte) 0xb1,(byte) 0x51,(byte) 0x3a,(byte) 0xe6,
            (byte) 0x9f,(byte) 0x2e,(byte) 0xe6,(byte) 0x10,(byte) 0x3f,(byte) 0x69,(byte) 0x81,(byte) 0x76,
            (byte) 0xd3,(byte) 0xd6,(byte) 0xb1,(byte) 0xf7,(byte) 0x74,(byte) 0xc3,(byte) 0xbf,(byte) 0xda,
            (byte) 0xc1,(byte) 0x01,(byte) 0xa3,(byte) 0xf8,(byte) 0xfb,(byte) 0x95,(byte) 0xcc,(byte) 0x6a,
            (byte) 0x21,(byte) 0x39,(byte) 0xce,(byte) 0x99,(byte) 0xcb,(byte) 0x79,(byte) 0x63,(byte) 0xf7,
            (byte) 0xc0,(byte) 0x99,(byte) 0xf2,(byte) 0x35,(byte) 0x7a,(byte) 0x1a,(byte) 0x78,(byte) 0x10,
            (byte) 0x0f,(byte) 0xe1,(byte) 0xc4,(byte) 0x60,(byte) 0x89,(byte) 0x80,(byte) 0x49,(byte) 0x47,
            (byte) 0xd8,(byte) 0x9b,(byte) 0x12,(byte) 0xda,(byte) 0x90,(byte) 0x0a,(byte) 0xc0,(byte) 0xdf,
            (byte) 0x94,(byte) 0x18,(byte) 0xe5,(byte) 0x10,(byte) 0x37,(byte) 0x5d,(byte) 0x6b,(byte) 0xe7,
            (byte) 0xad,(byte) 0x0c,(byte) 0x48,(byte) 0x3f,(byte) 0x39,(byte) 0x92,(byte) 0x33,(byte) 0x79,
            (byte) 0x44,(byte) 0xa3,(byte) 0x3a,(byte) 0x75,(byte) 0x77,(byte) 0x32,(byte) 0x4a,(byte) 0x50,
            (byte) 0xf8,(byte) 0xbb,(byte) 0xd8,(byte) 0xb3,(byte) 0xe0,(byte) 0xd1,(byte) 0x0e,(byte) 0xc7,
            (byte) 0xa5,(byte) 0x1a,(byte) 0x98,(byte) 0x1d,(byte) 0x79,(byte) 0x4f,(byte) 0x06,(byte) 0x3a,
            (byte) 0xd1,(byte) 0xad,(byte) 0x7a,(byte) 0x8a,(byte) 0x69,(byte) 0x68,(byte) 0xad,(byte) 0xbd,
            (byte) 0x3e,(byte) 0x48,(byte) 0x0f,(byte) 0x3f,(byte) 0x11,(byte) 0x3e,(byte) 0x8a,(byte) 0x7a,
            (byte) 0x8b,(byte) 0xfa,(byte) 0xb6,(byte) 0x7a,(byte) 0x30,(byte) 0xd4,(byte) 0xf1,(byte) 0x73,
            (byte) 0x0b,(byte) 0xa1,(byte) 0x2d,(byte) 0x5a,(byte) 0xb1,(byte) 0xe7,(byte) 0xfa,(byte) 0x8f,
            (byte) 0x2b,(byte) 0xee,(byte) 0x32,(byte) 0x9d,(byte) 0x62,(byte) 0xe5,(byte) 0x80,(byte) 0x37,
            (byte) 0xed,(byte) 0x3b,(byte) 0xa2,(byte) 0xd2,(byte) 0xd2,(byte) 0xac,(byte) 0xd1,(byte) 0x15,
            (byte) 0xb7,(byte) 0x14,(byte) 0x9d,(byte) 0x22,(byte) 0x38,(byte) 0x06,(byte) 0xaf,(byte) 0x88,
            (byte) 0xc4,(byte) 0xcd,(byte) 0xe0,(byte) 0x4c,(byte) 0xbe,(byte) 0xb7,(byte) 0x70,(byte) 0x4d,
            (byte) 0x5e,(byte) 0x1f,(byte) 0x74,(byte) 0x89,(byte) 0x68,(byte) 0x4e,(byte) 0x13,(byte) 0x50,
            (byte) 0x2f,(byte) 0x2c,(byte) 0x27,(byte) 0x24,(byte) 0xfa,(byte) 0xa5,(byte) 0x01,(byte) 0x5a,
            (byte) 0xa2,(byte) 0xe6,(byte) 0x04,(byte) 0x17,(byte) 0x79,(byte) 0x97,(byte) 0x13,(byte) 0x5b,
            (byte) 0x9d,(byte) 0xe9,(byte) 0x67,(byte) 0xcc,(byte) 0xe8,(byte) 0x8f,(byte) 0x84,(byte) 0x79,
            (byte) 0x13
    };

    /**
     * The RSA exponent of the EPS public key (on provisioning protocol level, not transport level).
     */
    public static final byte[] CFG_RSA_KEY_EXPONENT = {
            (byte) 0x01, (byte) 0x00, (byte) 0x01

    };

    /**
     * This configuration will allow to weaken TLS configuration for debug purposes. It’s not allowed to modify in release mode.
     */
    public static final MobileTlsConfiguration CFG_TLS_CONFIGURATION = new MobileTlsConfiguration(10000);

    /**
     * Gets the custom fingerprint data.
     */
    public static final byte[] CFG_CUSTOM_FINGERPRINT_DATA = "CUSTOM_FINGERPRINT_DATA".getBytes(StandardCharsets.UTF_8);

    /**
     * Gets the domain.
     */
    public static final String CFG_DOMAIN = "bradesco";

    /**
     * Gets the timestep.
     */
    public static final int CFG_TOTP_TIME_STEP = 30;

    /**
     * Gets the totp length.
     */
    public static final int CFG_TOTP_LENGTH = 6;

    /**
     * Gets authentication URL where the generated OTP is validated.
     */
    public static final String CFG_IDCLOUD_URL = "https://scs-ol-demo.rnd.gemaltodigitalbankingidcloud.com/scs/v1/scenarios";

    public static final String CFG_BASIC_AUTH_JWT = "eyJhbGciOiJFUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImJhbmNvbmV4dCJ9.eyJ0ZW5hbnRJZCI6ImJhbmNvbmV4dCIsImp0aSI6ImJhbmNvbmV4dC1iYW5jb25leHQtYmFuY29uZXh0LTQ1NzA3NjcwMjQiLCJyb2xlcyI6WyJzY3M6ZXhlY3V0ZVNjZW5hcmlvIl0sImlhdCI6MTY1NDgzOTczNCwiZXhwIjoxNjY1MjA3NzM0LCJhdWQiOiJzY3MiLCJpc3MiOiJiYW5jb25leHQtYmFuY29uZXh0Iiwic3ViIjoiYmFuY29uZXh0In0.TlXo0OGdc_OXu2zlfJKnEyP2AE-wiqNix52dJcVY2aaQUCA_hFY9I6fYOihVaX2tzJA_faHnxTOITR7-Lqcq8w";

    public static final String CFG_BASIC_AUTH_API_KEY = "8537d84d-11a6-4b8e-ad88-34aacca287a1";

}
