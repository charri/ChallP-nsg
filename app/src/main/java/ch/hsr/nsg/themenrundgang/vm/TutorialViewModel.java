package ch.hsr.nsg.themenrundgang.vm;

import ch.hsr.nsg.themenrundgang.applicationService.NsgApi;
import ch.hsr.nsg.themenrundgang.model.Repositories;

public class TutorialViewModel {

	private final NsgApi nsgApi;
	private final Repositories repos;
	
	public TutorialViewModel(NsgApi nsgApi, Repositories repos) {
		this.nsgApi = nsgApi;
		this.repos = repos;
	}
	
	public NsgApi getApi() {
		return nsgApi;
	}
	
	public Repositories getRepositories() {
		return repos;
	}
}
