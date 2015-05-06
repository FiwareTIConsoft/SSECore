/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tilab.ca.sse.core.rest;

/**
 *
 * @author riccardo
 */
public class ClassifyInput {
	private String text, lang;
	private Integer numTopics;

	public String getText() {
		return text;
	}

	public void setText(String s) {
		text = s;
	}

	public Integer getNumTopics() {
		return numTopics;
	}

	public void setNumTopics(Integer s) {
		numTopics = s;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String s) {
		lang = s;
	}
}
