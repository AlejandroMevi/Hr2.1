package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.massiveInputPutput.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MassiveInputOutputVM: ViewModel()  {
    var dataMassiveFilter= MutableLiveData<List<Int>>(null)
        private set
}