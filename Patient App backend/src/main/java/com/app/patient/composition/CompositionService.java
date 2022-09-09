package com.app.patient.composition;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.patient.healthRecord.HealthRecord;
import com.app.patient.healthRecord.HealthRecordDao;

@Service
public class CompositionService {

	@Autowired
	CompositionRepo repo;

	@Autowired
	HealthRecordDao healthRecorDao;

	public Boolean saveComposition(Composition c) {
		Composition comp = repo.save(c);
		if (comp != null) {
			return true;
		}
		return false;
	}

	public List<Composition> getComposition(HealthRecord h) {
		List<Composition> compList = repo.findByHealth(h);
		
		if (compList != null && compList.size() > 0) {
			return compList;
		}
		return compList;
	}

	public Composition getCompositionById(String compId) {
		Composition comp = repo.findByCompositionId(compId);
	
		if (comp != null) {
			return comp;
		}
		return comp;
	}

	@SuppressWarnings("null")
	public List<Composition> getCompositionList(String episodeId)

	{

		HealthRecord h = new HealthRecord();
		h = healthRecorDao.getHealth(episodeId);
		if (h.getEpisodeId() != null) {

			List<Composition> compStr = new ArrayList<Composition>();
			List<Composition> compList = repo.findByHealth(h);
			System.out.println("compList" + compList);
			if (compList != null && compList.size() > 0) {
				for (int i = 0; i < compList.size(); i++) {

					Composition addComp = new Composition();
					Composition c = compList.get(i);
					addComp.setCompositionId(c.getCompositionId());
					addComp.setTemplateId(c.getTemplateId());
					compStr.add(addComp);
				}

				if (compStr.size() > 0) {
					return compStr;
				}

			}

		}
		return null;

	}
}