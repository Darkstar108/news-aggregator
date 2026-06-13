type InformationPanelProps = {
  message: string;
  imgUrl?: string;
};

export default function InformationPanel({
  message = "",
  imgUrl,
}: InformationPanelProps) {
  if (!imgUrl) {
    return (
      <div>
        <p>{message}</p>
      </div>
    );
  } else {
    return (
      <div>
        <img src={imgUrl} alt="Image for messages: ${message}" />
        <p>{message}</p>
      </div>
    );
  }
}
