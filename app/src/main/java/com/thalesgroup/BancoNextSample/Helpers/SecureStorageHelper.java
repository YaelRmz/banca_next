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

import com.gemalto.idp.mobile.core.IdpCore;
import com.gemalto.idp.mobile.core.devicefingerprint.DeviceFingerprintException;
import com.gemalto.idp.mobile.core.passwordmanager.PasswordManagerException;
import com.gemalto.idp.mobile.core.util.SecureByteArray;
import com.gemalto.idp.mobile.core.util.SecureString;
import com.gemalto.idp.mobile.securestorage.IdpSecureStorageException;
import com.gemalto.idp.mobile.securestorage.PropertyStorage;
import com.gemalto.idp.mobile.securestorage.SecureStorageManager;
import com.gemalto.idp.mobile.securestorage.SecureStorageModule;

import java.nio.charset.StandardCharsets;

public class SecureStorageHelper {

    //region Defines

    private static final String EXAMPLE_STORAGE = "ExampleStorage";
    private static final String EXAMPLE_PIN_KEY = "ExamplePinKey";

    //endregion

    //region Public API

    public static String readPin() {
        return readStringForKey(EXAMPLE_PIN_KEY);
    }

    public static boolean writePin(final String pin) {
        return writeString(pin, EXAMPLE_PIN_KEY);
    }

    //endregion

    //region Private Helpers

    private static PropertyStorage getAndOpenStorage() throws IdpSecureStorageException, PasswordManagerException, DeviceFingerprintException {
        final SecureStorageManager manager = SecureStorageModule.create().getSecureStorageManager();
        final PropertyStorage storage = manager.getPropertyStorage(EXAMPLE_STORAGE);

        storage.open();

        return storage;
    }

    private static String readStringForKey(final String key) {
        try {
            final PropertyStorage storage = getAndOpenStorage();
            final SecureByteArray value = storage.readProperty(key.getBytes(StandardCharsets.UTF_8));
            final String retValue = new String(value.toByteArray(), StandardCharsets.UTF_8);

            value.wipe();
            storage.close();

            return retValue;
        } catch (final IdpSecureStorageException | PasswordManagerException | DeviceFingerprintException exception) {
            return null;
        }
    }

    private static boolean writeString(final String value, final String key) {
        try {
            final PropertyStorage storage = getAndOpenStorage();
            final SecureString secureString = IdpCore.getInstance().getSecureContainerFactory().fromString(value);
            storage.writeProperty(key.getBytes(StandardCharsets.UTF_8), secureString, true);
            storage.close();
            return true;
        } catch (final IdpSecureStorageException | PasswordManagerException | DeviceFingerprintException exception) {
            return false;
        }
    }

    //endregion
}
