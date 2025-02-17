import { InjectionToken, EventEmitter } from '@angular/core';

export const SIGNAL_TOKEN = new InjectionToken<EventEmitter<string>>('SignalToken');
