/*
 * Copyright © 2018 - 2020 hearX IP (Pty) Ltd.
 * Copyright subsists in this work and it is copyright protected under the Berne Convention.  No part of this work may be reproduced, published, performed, broadcasted, adapted or transmitted in any form or by any means, electronic or mechanical, including photocopying, recording or by any information storage and retrieval system, without permission in writing from the copyright owner
 * hearX Group (Pty) Ltd.
 * info@hearxgroup.com
 */

package com.davidhowe.cryeate.repositories

import android.content.Context
import android.content.Context.MODE_PRIVATE
import javax.inject.Inject

private const val PREF_FILE_NAME = "cryeate_prefs_v1"
private const val KEY_FIRST_LAUNCH = "KEY_FIRST_LAUNCH"

class SharedPrefsRepo @Inject constructor(private val context: Context) {
    fun isFirstLaunch() : Boolean {
        return context.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE).getBoolean(KEY_FIRST_LAUNCH, true)
    }

    fun setFirstLaunch(firstLaunch : Boolean) {
        context.getSharedPreferences(KEY_FIRST_LAUNCH, MODE_PRIVATE).edit().putBoolean(KEY_FIRST_LAUNCH, firstLaunch).apply()
    }
}