class S3Client {
  constructor(url) {
    this.url = url;
  }

  async getData() {
    try {
      const res = await fetch(this.url);
      if (!res.ok) throw new Error('Failed to fetch S3 data');
      return await res.json();
    } catch (e) {
      return [];
    }
  }
}

export const s3Client = new S3Client('https://fpl-site-2025.s3.us-east-1.amazonaws.com/fake-fpl-data.json');
