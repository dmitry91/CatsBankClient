package com.dmitry.catsbankclient.presenter;

import com.dmitry.catsbankclient.models.ModelCat;

public class PresenterCats {

    private ModelCat modelCat;

    public PresenterCats() {
    }

    public void setModelCats(ModelCat modelCat) {
        this.modelCat = modelCat;
    }

    public void getAllCats(){
        modelCat.getAllCats();
    }

    public void findCatOnListById(int idCat){
        modelCat.findCatOnListById(idCat);
    }

    public void updateCat(int id, String text, String path){
        modelCat.updateCat(id, text, path);
    }

    public void deleteCat(int idCat){
        modelCat.deleteCat(idCat);
    }

    public void saveCat(String text, String path){
        modelCat.saveCat(text, path);
    }
}
