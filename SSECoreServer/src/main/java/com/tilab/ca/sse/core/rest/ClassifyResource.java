/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tilab.ca.sse.core.rest;

import com.tilab.ca.sse.core.classify.Classifier;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author riccardo
 */
@Path("classify")
public class ClassifyResource {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ClassifyOutput> classify(ClassifyInput input) {
		return classifyAdapter(
			new Classifier(input.getLang())
					.classify(	input.getText(),
								input.getNumTopics()));
	}

	@POST
	@Path("short")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ClassifyOutput> classifyShortText(ClassifyInput input) {
		return classifyAdapter(
			new Classifier(input.getLang())
					.classifyShortText(	input.getText(), 
										input.getNumTopics()));
	}

	private List<ClassifyOutput> classifyAdapter(List<String[]> list) {
		return list.stream().map(strings -> {
			ClassifyOutput output = new ClassifyOutput();
			output.setUri(strings[0]);
			output.setLabel(strings[1]);
			output.setTitle(strings[2]);
			output.setScore(strings[3]);
			output.setMergedTypes(strings[4]);
			output.setImage(strings[5]);
			output.setWikilink(strings[6]);
                        return output;
		}).collect(toList());
	}
}
