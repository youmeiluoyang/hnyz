package com.dg11185.util.filemonitor.callback;


import com.dg11185.util.filemonitor.vo.FileChangeObject;

public interface FileChangeCallback {
	void fileChanged(FileChangeObject fco);
}
