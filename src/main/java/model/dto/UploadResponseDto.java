package model.dto;

public record UploadResponseDto(String response, String fileName) {
    @Override
    public String toString() {
        return "response " + response + ", fileName " + fileName;
    }

}
