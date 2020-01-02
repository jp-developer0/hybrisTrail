/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
/**
 * This class can be used as a mock for a variet of event based smartedit services
 * $rootscope - $on events
 * CrossFrameEventService
 * MessageGateway
 */
export class GenericEventer {

	eventsCbMap: {[index: string]: () => any} = {};

	public $on = (eventName: string, cb: () => any) => {
		this.eventsCbMap[eventName] = cb;
	}

	public subscribe = (eventName: string, cb: () => any) => {
		this.eventsCbMap[eventName] = cb;
	}

	public triggerEvent = (eventName: string) => {
		this.eventsCbMap[eventName]();
	}

}