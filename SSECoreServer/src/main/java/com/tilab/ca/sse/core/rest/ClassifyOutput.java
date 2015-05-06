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
public class ClassifyOutput {
	private String uri, label, title, score, mergedTypes, image, wikilink;

	public String getUri() {
		return uri;
	}

	public void setUri(String s) {
		uri = s;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String s) {
		label = s;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String s) {
		title = s;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String s) {
		score = s;
	}

	public String getMergedTypes() {
		return mergedTypes;
	}

	public void setMergedTypes(String s) {
		mergedTypes = s;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String s) {
		image = s;
	}

	public String getWikilink() {
		return wikilink;
	}

	public void setWikilink(String s) {
		wikilink = s;
	}
}
