package qa.qcri.mm.api.util;

//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.type.TypeReference;


public class TaskManagerEntityMapper {

	public TaskManagerEntityMapper() {}
   /**
	public <E> E deSerializeList(String jsonString, TypeReference<E> type) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			if (jsonString != null) {
				E docList = mapper.readValue(jsonString, type);
				return docList;
			}	
		} catch (IOException e) {
			System.err.println("JSON deserialization exception");
			e.printStackTrace();
		}
		return null;
	}

	public <E> E deSerialize(String jsonString, Class<E> entityType) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			if (jsonString != null) {
				E qa.qcri.mm.media.entity = mapper.readValue(jsonString, entityType);
				return qa.qcri.mm.media.entity;
			}	
		} catch (IOException e) {
			System.err.println("JSON deserialization exception");
			e.printStackTrace();
		}
		return null;
	}

	public <E> String serializeTask(E task) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			if (task != null) jsonString = mapper.writeValueAsString(task);
		} catch (IOException e) {
			System.err.println("JSON serialization exception");
			e.printStackTrace();
		}
		return jsonString;
	}

	public Document transformDocument(Document document) {
		Document doc = new Document();
		if (document != null) {
			doc.setDocumentID(document.getDocumentID());
			doc.setCrisisID(document.getCrisisID());
			doc.setDoctype(document.getDoctype());
			doc.setData(document.getData());
			doc.setEvaluationSet(document.isEvaluationSet());
			doc.setGeoFeatures(document.getGeoFeatures());
			doc.setLanguage(document.getLanguage());
			doc.setHasHumanLabels(document.isHasHumanLabels());

			doc.setReceivedAt(document.getReceivedAt());
			doc.setSourceIP(document.getSourceIP());
			doc.setWordFeatures(document.getWordFeatures());
			doc.setValueAsTrainingSample(document.getValueAsTrainingSample());
			doc.setTaskAssignment(transformTaskAssignment(document.getTaskAssignment()));

			return doc;
		} 
		return null;
	}

	public TaskAssignment transformTaskAssignment(TaskAssignment t) {
		if (t != null) {
			TaskAssignment taskAssignment  = new TaskAssignment(t.getDocumentID(), t.getUserID(), t.getAssignedAt());
			return taskAssignment;
		}
		return null;
	}

	public TaskAnswer transformTaskAnswer(TaskAnswer t) {
		if (t != null) {
			TaskAnswer taskAnswer = new TaskAnswer(t.getDocumentID(), t.getUserID(), t.getAnswer());
			return taskAnswer;
		}
		return null;
	}
	
	public DocumentNominalLabel transformDocumentNominalLabel(DocumentNominalLabel doc) {
		if (doc != null) {
			DocumentNominalLabel nominalDoc = new DocumentNominalLabel(doc.getDocumentID(), doc.getNominalLabelID(), doc.getUserID());
			return nominalDoc;
		}
		return null;
	}
	
	public static void main(String args[]) {
		TaskManagerEntityMapper mapper = new TaskManagerEntityMapper();
		Document doc = new Document(12345678L, false);
		Document doc2 = new Document(12345679L, true);
		String jsonString = mapper.serializeTask(doc);
		Document newDoc1 = mapper.transformDocument(doc);
		System.out.println("New document 1 = " + newDoc1.getDocumentID());

		Document newDoc2 = mapper.deSerialize(jsonString, Document.class);
		System.out.println("New document 2 = " + newDoc2.getDocumentID());

		List<Document> docList = new ArrayList<Document>();
		docList.add(doc);
		docList.add(doc2);
		String jsonString2 = mapper.serializeTask(docList);

		List<Document> newDocList = mapper.deSerializeList(jsonString2, new TypeReference<List<Document>>() {});
		for (Document d: newDocList) {
			System.out.println("New document = " + d.getDocumentID());
		}
		
	}
    **/
}
