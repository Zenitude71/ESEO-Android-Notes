package com.example.notes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// L'annotation @HiltAndroidApp est obligatoire pour déclencher la génération de Hilt
@HiltAndroidApp
class NotesApplication : Application()