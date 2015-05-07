/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tilab.ca.sse.core.classify.properties;

/**
 *
 * @author riccardo
 */
/**
	 * Replace these static String with owner-like interface
	 * 
	 * public static String CORPUS_INDEX_IT;
	public static String KB_IT;
	public static String RESIDUAL_KB_IT;
	public static Set<String> STOPWORDS_IT;

	public static String CORPUS_INDEX_EN;
	public static String KB_EN;
	public static String RESIDUAL_KB_EN;
	public static Set<String> STOPWORDS_EN;
	 */

public interface Config {
	
	String corpusIndexIT();
	String kbIT();
	String residualKBIT();
	String stopWordsIT();
	
	String corpusIndexEN();
	String kbEN();
	String residualKBEN();
	String stopWordsEN();
	
}
